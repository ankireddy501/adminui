import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SubscriptionRequests } from './subscription-requests.model';
import { SubscriptionRequestsPopupService } from './subscription-requests-popup.service';
import { SubscriptionRequestsService } from './subscription-requests.service';

@Component({
    selector: 'jhi-subscription-requests-dialog',
    templateUrl: './subscription-requests-dialog.component.html'
})
export class SubscriptionRequestsDialogComponent implements OnInit {

    subscriptionRequests: SubscriptionRequests;
    isSaving: boolean;
    requestedDateDp: any;
    approvalDateDp: any;
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private subscriptionRequestsService: SubscriptionRequestsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.subscriptionRequests.id !== undefined) {
            this.subscribeToSaveResponse(
                this.subscriptionRequestsService.update(this.subscriptionRequests));
        } else {
            this.subscribeToSaveResponse(
                this.subscriptionRequestsService.create(this.subscriptionRequests));
        }
    }

    private subscribeToSaveResponse(result: Observable<SubscriptionRequests>) {
        result.subscribe((res: SubscriptionRequests) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SubscriptionRequests) {
        this.eventManager.broadcast({ name: 'subscriptionRequestsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-subscription-requests-popup',
    template: ''
})
export class SubscriptionRequestsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subscriptionRequestsPopupService: SubscriptionRequestsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.subscriptionRequestsPopupService
                    .open(SubscriptionRequestsDialogComponent as Component, params['id']);
            } else {
                this.subscriptionRequestsPopupService
                    .open(SubscriptionRequestsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

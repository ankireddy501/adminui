import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SubscriptionRequests } from './subscription-requests.model';
import { SubscriptionRequestsPopupService } from './subscription-requests-popup.service';
import { SubscriptionRequestsService } from './subscription-requests.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-subscription-requests-dialog',
    templateUrl: './subscription-requests-dialog.component.html'
})
export class SubscriptionRequestsDialogComponent implements OnInit {

    subscriptionRequests: SubscriptionRequests;
    isSaving: boolean;

    users: User[];
    requestedDateDp: any;
    approvalDateDp: any;
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private subscriptionRequestsService: SubscriptionRequestsService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
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

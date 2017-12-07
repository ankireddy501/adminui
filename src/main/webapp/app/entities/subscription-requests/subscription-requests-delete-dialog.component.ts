import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SubscriptionRequests } from './subscription-requests.model';
import { SubscriptionRequestsPopupService } from './subscription-requests-popup.service';
import { SubscriptionRequestsService } from './subscription-requests.service';

@Component({
    selector: 'jhi-subscription-requests-delete-dialog',
    templateUrl: './subscription-requests-delete-dialog.component.html'
})
export class SubscriptionRequestsDeleteDialogComponent {

    subscriptionRequests: SubscriptionRequests;

    constructor(
        private subscriptionRequestsService: SubscriptionRequestsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.subscriptionRequestsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'subscriptionRequestsListModification',
                content: 'Deleted an subscriptionRequests'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-subscription-requests-delete-popup',
    template: ''
})
export class SubscriptionRequestsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subscriptionRequestsPopupService: SubscriptionRequestsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.subscriptionRequestsPopupService
                .open(SubscriptionRequestsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

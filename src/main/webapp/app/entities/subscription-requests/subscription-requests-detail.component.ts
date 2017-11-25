import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SubscriptionRequests } from './subscription-requests.model';
import { SubscriptionRequestsService } from './subscription-requests.service';

@Component({
    selector: 'jhi-subscription-requests-detail',
    templateUrl: './subscription-requests-detail.component.html'
})
export class SubscriptionRequestsDetailComponent implements OnInit, OnDestroy {

    subscriptionRequests: SubscriptionRequests;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private subscriptionRequestsService: SubscriptionRequestsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSubscriptionRequests();
    }

    load(id) {
        this.subscriptionRequestsService.find(id).subscribe((subscriptionRequests) => {
            this.subscriptionRequests = subscriptionRequests;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSubscriptionRequests() {
        this.eventSubscriber = this.eventManager.subscribe(
            'subscriptionRequestsListModification',
            (response) => this.load(this.subscriptionRequests.id)
        );
    }
}

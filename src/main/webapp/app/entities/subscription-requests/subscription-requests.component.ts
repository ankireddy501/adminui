import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SubscriptionRequests } from './subscription-requests.model';
import { SubscriptionRequestsService } from './subscription-requests.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-subscription-requests',
    templateUrl: './subscription-requests.component.html'
})
export class SubscriptionRequestsComponent implements OnInit, OnDestroy {
subscriptionRequests: SubscriptionRequests[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private subscriptionRequestsService: SubscriptionRequestsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.subscriptionRequestsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.subscriptionRequests = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSubscriptionRequests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SubscriptionRequests) {
        return item.id;
    }
    registerChangeInSubscriptionRequests() {
        this.eventSubscriber = this.eventManager.subscribe('subscriptionRequestsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

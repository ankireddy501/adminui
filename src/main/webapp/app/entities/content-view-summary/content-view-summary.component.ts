import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ContentViewSummary } from './content-view-summary.model';
import { ContentViewSummaryService } from './content-view-summary.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-content-view-summary',
    templateUrl: './content-view-summary.component.html'
})
export class ContentViewSummaryComponent implements OnInit, OnDestroy {
contentViewSummaries: ContentViewSummary[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contentViewSummaryService: ContentViewSummaryService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.contentViewSummaryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.contentViewSummaries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInContentViewSummaries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ContentViewSummary) {
        return item.id;
    }
    registerChangeInContentViewSummaries() {
        this.eventSubscriber = this.eventManager.subscribe('contentViewSummaryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

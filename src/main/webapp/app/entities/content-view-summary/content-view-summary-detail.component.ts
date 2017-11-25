import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ContentViewSummary } from './content-view-summary.model';
import { ContentViewSummaryService } from './content-view-summary.service';

@Component({
    selector: 'jhi-content-view-summary-detail',
    templateUrl: './content-view-summary-detail.component.html'
})
export class ContentViewSummaryDetailComponent implements OnInit, OnDestroy {

    contentViewSummary: ContentViewSummary;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contentViewSummaryService: ContentViewSummaryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContentViewSummaries();
    }

    load(id) {
        this.contentViewSummaryService.find(id).subscribe((contentViewSummary) => {
            this.contentViewSummary = contentViewSummary;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContentViewSummaries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contentViewSummaryListModification',
            (response) => this.load(this.contentViewSummary.id)
        );
    }
}

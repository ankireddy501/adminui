import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContentViewSummary } from './content-view-summary.model';
import { ContentViewSummaryPopupService } from './content-view-summary-popup.service';
import { ContentViewSummaryService } from './content-view-summary.service';

@Component({
    selector: 'jhi-content-view-summary-delete-dialog',
    templateUrl: './content-view-summary-delete-dialog.component.html'
})
export class ContentViewSummaryDeleteDialogComponent {

    contentViewSummary: ContentViewSummary;

    constructor(
        private contentViewSummaryService: ContentViewSummaryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contentViewSummaryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contentViewSummaryListModification',
                content: 'Deleted an contentViewSummary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-content-view-summary-delete-popup',
    template: ''
})
export class ContentViewSummaryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentViewSummaryPopupService: ContentViewSummaryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contentViewSummaryPopupService
                .open(ContentViewSummaryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContentViewSummary } from './content-view-summary.model';
import { ContentViewSummaryPopupService } from './content-view-summary-popup.service';
import { ContentViewSummaryService } from './content-view-summary.service';

@Component({
    selector: 'jhi-content-view-summary-dialog',
    templateUrl: './content-view-summary-dialog.component.html'
})
export class ContentViewSummaryDialogComponent implements OnInit {

    contentViewSummary: ContentViewSummary;
    isSaving: boolean;
    viewDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contentViewSummaryService: ContentViewSummaryService,
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
        if (this.contentViewSummary.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contentViewSummaryService.update(this.contentViewSummary));
        } else {
            this.subscribeToSaveResponse(
                this.contentViewSummaryService.create(this.contentViewSummary));
        }
    }

    private subscribeToSaveResponse(result: Observable<ContentViewSummary>) {
        result.subscribe((res: ContentViewSummary) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ContentViewSummary) {
        this.eventManager.broadcast({ name: 'contentViewSummaryListModification', content: 'OK'});
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
    selector: 'jhi-content-view-summary-popup',
    template: ''
})
export class ContentViewSummaryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentViewSummaryPopupService: ContentViewSummaryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contentViewSummaryPopupService
                    .open(ContentViewSummaryDialogComponent as Component, params['id']);
            } else {
                this.contentViewSummaryPopupService
                    .open(ContentViewSummaryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

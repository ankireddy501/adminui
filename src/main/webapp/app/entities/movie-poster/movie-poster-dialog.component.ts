import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MoviePoster } from './movie-poster.model';
import { MoviePosterPopupService } from './movie-poster-popup.service';
import { MoviePosterService } from './movie-poster.service';
import { MovieContent, MovieContentService } from '../movie-content';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-movie-poster-dialog',
    templateUrl: './movie-poster-dialog.component.html'
})
export class MoviePosterDialogComponent implements OnInit {

    moviePoster: MoviePoster;
    isSaving: boolean;

    moviecontents: MovieContent[];
    creationDateDp: any;
    updateDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private moviePosterService: MoviePosterService,
        private movieContentService: MovieContentService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.movieContentService.query()
            .subscribe((res: ResponseWrapper) => { this.moviecontents = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.moviePoster, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.moviePoster.id !== undefined) {
            this.subscribeToSaveResponse(
                this.moviePosterService.update(this.moviePoster));
        } else {
            this.subscribeToSaveResponse(
                this.moviePosterService.create(this.moviePoster));
        }
    }

    private subscribeToSaveResponse(result: Observable<MoviePoster>) {
        result.subscribe((res: MoviePoster) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MoviePoster) {
        this.eventManager.broadcast({ name: 'moviePosterListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMovieContentById(index: number, item: MovieContent) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-movie-poster-popup',
    template: ''
})
export class MoviePosterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moviePosterPopupService: MoviePosterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.moviePosterPopupService
                    .open(MoviePosterDialogComponent as Component, params['id']);
            } else {
                this.moviePosterPopupService
                    .open(MoviePosterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

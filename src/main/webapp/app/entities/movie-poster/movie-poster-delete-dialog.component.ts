import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MoviePoster } from './movie-poster.model';
import { MoviePosterPopupService } from './movie-poster-popup.service';
import { MoviePosterService } from './movie-poster.service';

@Component({
    selector: 'jhi-movie-poster-delete-dialog',
    templateUrl: './movie-poster-delete-dialog.component.html'
})
export class MoviePosterDeleteDialogComponent {

    moviePoster: MoviePoster;

    constructor(
        private moviePosterService: MoviePosterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moviePosterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'moviePosterListModification',
                content: 'Deleted an moviePoster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-poster-delete-popup',
    template: ''
})
export class MoviePosterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moviePosterPopupService: MoviePosterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.moviePosterPopupService
                .open(MoviePosterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

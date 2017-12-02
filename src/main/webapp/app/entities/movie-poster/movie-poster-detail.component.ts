import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MoviePoster } from './movie-poster.model';
import { MoviePosterService } from './movie-poster.service';

@Component({
    selector: 'jhi-movie-poster-detail',
    templateUrl: './movie-poster-detail.component.html'
})
export class MoviePosterDetailComponent implements OnInit, OnDestroy {

    moviePoster: MoviePoster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private moviePosterService: MoviePosterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMoviePosters();
    }

    load(id) {
        this.moviePosterService.find(id).subscribe((moviePoster) => {
            this.moviePoster = moviePoster;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMoviePosters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'moviePosterListModification',
            (response) => this.load(this.moviePoster.id)
        );
    }
}

import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MoviePoster } from './movie-poster.model';
import { MoviePosterService } from './movie-poster.service';

@Injectable()
export class MoviePosterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private moviePosterService: MoviePosterService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.moviePosterService.find(id).subscribe((moviePoster) => {
                    if (moviePoster.creationDate) {
                        moviePoster.creationDate = {
                            year: moviePoster.creationDate.getFullYear(),
                            month: moviePoster.creationDate.getMonth() + 1,
                            day: moviePoster.creationDate.getDate()
                        };
                    }
                    if (moviePoster.updateDate) {
                        moviePoster.updateDate = {
                            year: moviePoster.updateDate.getFullYear(),
                            month: moviePoster.updateDate.getMonth() + 1,
                            day: moviePoster.updateDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.moviePosterModalRef(component, moviePoster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.moviePosterModalRef(component, new MoviePoster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    moviePosterModalRef(component: Component, moviePoster: MoviePoster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.moviePoster = moviePoster;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

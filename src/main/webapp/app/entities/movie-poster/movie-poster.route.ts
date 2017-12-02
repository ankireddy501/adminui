import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MoviePosterComponent } from './movie-poster.component';
import { MoviePosterDetailComponent } from './movie-poster-detail.component';
import { MoviePosterPopupComponent } from './movie-poster-dialog.component';
import { MoviePosterDeletePopupComponent } from './movie-poster-delete-dialog.component';

export const moviePosterRoute: Routes = [
    {
        path: 'movie-poster',
        component: MoviePosterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePosters'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'movie-poster/:id',
        component: MoviePosterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePosters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moviePosterPopupRoute: Routes = [
    {
        path: 'movie-poster-new',
        component: MoviePosterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePosters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-poster/:id/edit',
        component: MoviePosterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePosters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-poster/:id/delete',
        component: MoviePosterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePosters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

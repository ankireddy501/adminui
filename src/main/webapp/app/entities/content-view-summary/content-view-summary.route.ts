import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContentViewSummaryComponent } from './content-view-summary.component';
import { ContentViewSummaryDetailComponent } from './content-view-summary-detail.component';
import { ContentViewSummaryPopupComponent } from './content-view-summary-dialog.component';
import { ContentViewSummaryDeletePopupComponent } from './content-view-summary-delete-dialog.component';

export const contentViewSummaryRoute: Routes = [
    {
        path: 'content-view-summary',
        component: ContentViewSummaryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentViewSummaries'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'content-view-summary/:id',
        component: ContentViewSummaryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentViewSummaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contentViewSummaryPopupRoute: Routes = [
    {
        path: 'content-view-summary-new',
        component: ContentViewSummaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentViewSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'content-view-summary/:id/edit',
        component: ContentViewSummaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentViewSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'content-view-summary/:id/delete',
        component: ContentViewSummaryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentViewSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

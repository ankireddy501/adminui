import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SubscriptionRequestsComponent } from './subscription-requests.component';
import { SubscriptionRequestsDetailComponent } from './subscription-requests-detail.component';
import { SubscriptionRequestsPopupComponent } from './subscription-requests-dialog.component';
import { SubscriptionRequestsDeletePopupComponent } from './subscription-requests-delete-dialog.component';

export const subscriptionRequestsRoute: Routes = [
    {
        path: 'subscription-requests',
        component: SubscriptionRequestsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubscriptionRequests'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'subscription-requests/:id',
        component: SubscriptionRequestsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubscriptionRequests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subscriptionRequestsPopupRoute: Routes = [
    {
        path: 'subscription-requests-new',
        component: SubscriptionRequestsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubscriptionRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subscription-requests/:id/edit',
        component: SubscriptionRequestsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubscriptionRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subscription-requests/:id/delete',
        component: SubscriptionRequestsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubscriptionRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

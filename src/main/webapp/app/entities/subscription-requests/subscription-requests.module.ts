import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminuiSharedModule } from '../../shared';
import {
    SubscriptionRequestsService,
    SubscriptionRequestsPopupService,
    SubscriptionRequestsComponent,
    SubscriptionRequestsDetailComponent,
    SubscriptionRequestsDialogComponent,
    SubscriptionRequestsPopupComponent,
    SubscriptionRequestsDeletePopupComponent,
    SubscriptionRequestsDeleteDialogComponent,
    subscriptionRequestsRoute,
    subscriptionRequestsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...subscriptionRequestsRoute,
    ...subscriptionRequestsPopupRoute,
];

@NgModule({
    imports: [
        AdminuiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SubscriptionRequestsComponent,
        SubscriptionRequestsDetailComponent,
        SubscriptionRequestsDialogComponent,
        SubscriptionRequestsDeleteDialogComponent,
        SubscriptionRequestsPopupComponent,
        SubscriptionRequestsDeletePopupComponent,
    ],
    entryComponents: [
        SubscriptionRequestsComponent,
        SubscriptionRequestsDialogComponent,
        SubscriptionRequestsPopupComponent,
        SubscriptionRequestsDeleteDialogComponent,
        SubscriptionRequestsDeletePopupComponent,
    ],
    providers: [
        SubscriptionRequestsService,
        SubscriptionRequestsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminuiSubscriptionRequestsModule {}

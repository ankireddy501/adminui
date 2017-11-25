import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminuiSharedModule } from '../../shared';
import {
    ContentViewSummaryService,
    ContentViewSummaryPopupService,
    ContentViewSummaryComponent,
    ContentViewSummaryDetailComponent,
    ContentViewSummaryDialogComponent,
    ContentViewSummaryPopupComponent,
    ContentViewSummaryDeletePopupComponent,
    ContentViewSummaryDeleteDialogComponent,
    contentViewSummaryRoute,
    contentViewSummaryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...contentViewSummaryRoute,
    ...contentViewSummaryPopupRoute,
];

@NgModule({
    imports: [
        AdminuiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContentViewSummaryComponent,
        ContentViewSummaryDetailComponent,
        ContentViewSummaryDialogComponent,
        ContentViewSummaryDeleteDialogComponent,
        ContentViewSummaryPopupComponent,
        ContentViewSummaryDeletePopupComponent,
    ],
    entryComponents: [
        ContentViewSummaryComponent,
        ContentViewSummaryDialogComponent,
        ContentViewSummaryPopupComponent,
        ContentViewSummaryDeleteDialogComponent,
        ContentViewSummaryDeletePopupComponent,
    ],
    providers: [
        ContentViewSummaryService,
        ContentViewSummaryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminuiContentViewSummaryModule {}

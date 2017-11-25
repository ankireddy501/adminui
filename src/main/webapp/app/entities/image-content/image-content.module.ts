import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminuiSharedModule } from '../../shared';
import {
    ImageContentService,
    ImageContentPopupService,
    ImageContentComponent,
    ImageContentDetailComponent,
    ImageContentDialogComponent,
    ImageContentPopupComponent,
    ImageContentDeletePopupComponent,
    ImageContentDeleteDialogComponent,
    imageContentRoute,
    imageContentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...imageContentRoute,
    ...imageContentPopupRoute,
];

@NgModule({
    imports: [
        AdminuiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ImageContentComponent,
        ImageContentDetailComponent,
        ImageContentDialogComponent,
        ImageContentDeleteDialogComponent,
        ImageContentPopupComponent,
        ImageContentDeletePopupComponent,
    ],
    entryComponents: [
        ImageContentComponent,
        ImageContentDialogComponent,
        ImageContentPopupComponent,
        ImageContentDeleteDialogComponent,
        ImageContentDeletePopupComponent,
    ],
    providers: [
        ImageContentService,
        ImageContentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminuiImageContentModule {}

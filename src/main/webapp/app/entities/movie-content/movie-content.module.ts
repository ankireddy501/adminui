import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminuiSharedModule } from '../../shared';
import {
    MovieContentService,
    MovieContentPopupService,
    MovieContentComponent,
    MovieContentDetailComponent,
    MovieContentDialogComponent,
    MovieContentPopupComponent,
    MovieContentDeletePopupComponent,
    MovieContentDeleteDialogComponent,
    movieContentRoute,
    movieContentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...movieContentRoute,
    ...movieContentPopupRoute,
];

@NgModule({
    imports: [
        AdminuiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MovieContentComponent,
        MovieContentDetailComponent,
        MovieContentDialogComponent,
        MovieContentDeleteDialogComponent,
        MovieContentPopupComponent,
        MovieContentDeletePopupComponent,
    ],
    entryComponents: [
        MovieContentComponent,
        MovieContentDialogComponent,
        MovieContentPopupComponent,
        MovieContentDeleteDialogComponent,
        MovieContentDeletePopupComponent,
    ],
    providers: [
        MovieContentService,
        MovieContentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminuiMovieContentModule {}

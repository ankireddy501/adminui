import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ContentViewSummary } from './content-view-summary.model';
import { ContentViewSummaryService } from './content-view-summary.service';

@Injectable()
export class ContentViewSummaryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private contentViewSummaryService: ContentViewSummaryService

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
                this.contentViewSummaryService.find(id).subscribe((contentViewSummary) => {
                    if (contentViewSummary.viewDate) {
                        contentViewSummary.viewDate = {
                            year: contentViewSummary.viewDate.getFullYear(),
                            month: contentViewSummary.viewDate.getMonth() + 1,
                            day: contentViewSummary.viewDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.contentViewSummaryModalRef(component, contentViewSummary);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.contentViewSummaryModalRef(component, new ContentViewSummary());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    contentViewSummaryModalRef(component: Component, contentViewSummary: ContentViewSummary): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contentViewSummary = contentViewSummary;
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

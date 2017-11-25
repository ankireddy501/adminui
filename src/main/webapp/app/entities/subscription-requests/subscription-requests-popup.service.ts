import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SubscriptionRequests } from './subscription-requests.model';
import { SubscriptionRequestsService } from './subscription-requests.service';

@Injectable()
export class SubscriptionRequestsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private subscriptionRequestsService: SubscriptionRequestsService

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
                this.subscriptionRequestsService.find(id).subscribe((subscriptionRequests) => {
                    if (subscriptionRequests.requestedDate) {
                        subscriptionRequests.requestedDate = {
                            year: subscriptionRequests.requestedDate.getFullYear(),
                            month: subscriptionRequests.requestedDate.getMonth() + 1,
                            day: subscriptionRequests.requestedDate.getDate()
                        };
                    }
                    if (subscriptionRequests.approvalDate) {
                        subscriptionRequests.approvalDate = {
                            year: subscriptionRequests.approvalDate.getFullYear(),
                            month: subscriptionRequests.approvalDate.getMonth() + 1,
                            day: subscriptionRequests.approvalDate.getDate()
                        };
                    }
                    if (subscriptionRequests.startDate) {
                        subscriptionRequests.startDate = {
                            year: subscriptionRequests.startDate.getFullYear(),
                            month: subscriptionRequests.startDate.getMonth() + 1,
                            day: subscriptionRequests.startDate.getDate()
                        };
                    }
                    if (subscriptionRequests.endDate) {
                        subscriptionRequests.endDate = {
                            year: subscriptionRequests.endDate.getFullYear(),
                            month: subscriptionRequests.endDate.getMonth() + 1,
                            day: subscriptionRequests.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.subscriptionRequestsModalRef(component, subscriptionRequests);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.subscriptionRequestsModalRef(component, new SubscriptionRequests());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    subscriptionRequestsModalRef(component: Component, subscriptionRequests: SubscriptionRequests): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.subscriptionRequests = subscriptionRequests;
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

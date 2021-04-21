import { HttpResponse } from '@angular/common/http';
// import { i18nMetaToDocStmt } from '@angular/compiler/src/render3/view/i18n/meta';
import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'app/core/user/user.service';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';
import { CustomerComment } from 'app/shared/model/customer-comment.model';
/**
 *
 * @author QCR 
 * This component represent a single comment to display at the bottom of the book description page
 */
@Component({
  selector: 'jhi-customer-review-item',
  templateUrl: './customer-review-item.component.html',
  styleUrls: ['./customer-review-item.component.scss'],
})
export class CustomerReviewItemComponent implements OnInit {
  @Input() customerComment: CustomerComment | undefined;
  rating5b: boolean[] | undefined;
  username: string | undefined;
  nbStars! : number

  constructor(private customUserService: CustomUserService,
              private userService: UserService) {}

  ngOnInit(): void {
    if (this.customerComment?.customerId! >= 0) {
      this.customUserService.find(this.customerComment?.customerId!).subscribe(customUser => {
        if (customUser.body && customUser.body.userId! >= 0) {
          this.customUserService.findUsernameById(customUser.body.userId!).subscribe((res: HttpResponse<string>) => {
            if (res.body) {
              this.username = res.body;
            }
          });
        } else {
          this.username = '[unknown]';
        }
      });
    }
    if(this.customerComment?.rating)
      this.nbStars = Math.floor(this.customerComment?.rating)
  }

  counter(i: number | undefined): Array<any>{
    return new Array(i);
  }

}

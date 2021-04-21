import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IBook } from '../shared/model/book.model';
import { CustomerComment } from 'app/shared/model/customer-comment.model';
import { CustomerCommentService } from 'app/entities/customer-comment/customer-comment.service';

/**
 * @author QCR
 * Page de description d'un livre lorsque l'utilisateur qui sur un livre.
 * Contient les details concernant le livre, ainsi que les comments d'utilisateur.
 * Il doit aussi etre possible au client d'ajouter au panier un livre disponible
 * a partir de cette page.
 */
@Component({
  selector: 'jhi-book-details-page',
  templateUrl: './book-details-page.component.html',
  styleUrls: ['./book-details-page.component.scss'],
})
export class BookDetailsPageComponent implements OnInit {
  book: IBook | null | undefined;
  reviews! : CustomerComment[] | null

  constructor(private commentService : CustomerCommentService,
              protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    /**
     * book et customerComment sont recuperés via des Resolves
     * Voir : book-description-page.route.ts
     */
    this.activatedRoute.data.subscribe(({ book }) => {
      this.book = book;
    });

    if(this.book?.id){
      this.commentService.getbookComments(this.book?.id).subscribe(res => {
        this.reviews = res.body;
      });
    }
  }
}

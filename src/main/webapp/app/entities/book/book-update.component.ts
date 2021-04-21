import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBook, Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author/author.service';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountService } from 'app/entities/discount/discount.service';

import { AngularFireStorage, AngularFireStorageReference, AngularFireUploadTask } from '@angular/fire/storage';
import { UploadTaskSnapshot } from '@angular/fire/storage/interfaces';

type SelectableEntity = ICategory | IAuthor | IDiscount;

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html',
})
export class BookUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];
  authors: IAuthor[] = [];
  discounts: IDiscount[] = [];
  publicationDateDp: any;
  storageReference: AngularFireStorageReference | undefined;
  task: AngularFireUploadTask | undefined;
  downloadURL: Observable<string> | undefined;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    price: [null, [Validators.required]],
    rating: [],
    imageURL: [],
    language: [],
    format: [null, [Validators.required]],
    paperBackQuantity: [],
    publicationDate: [null, [Validators.required]],
    isbn: [],
    pages: [],
    otherDetails: [],
    categoryId: [],
    authors: [],
    discountId: [],
  });

  constructor(
    protected bookService: BookService,
    protected categoryService: CategoryService,
    protected authorService: AuthorService,
    protected discountService: DiscountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private firebaseStorage: AngularFireStorage
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));

      this.authorService.query().subscribe((res: HttpResponse<IAuthor[]>) => (this.authors = res.body || []));

      this.discountService.query().subscribe((res: HttpResponse<IDiscount[]>) => (this.discounts = res.body || []));
    });
  }

  updateForm(book: IBook): void {
    this.editForm.patchValue({
      id: book.id,
      title: book.title,
      price: book.price,
      rating: book.rating,
      imageURL: book.imageURL,
      language: book.language,
      format: book.format,
      paperBackQuantity: book.paperBackQuantity,
      publicationDate: book.publicationDate,
      isbn: book.isbn,
      pages: book.pages,
      otherDetails: book.otherDetails,
      categoryId: book.categoryId,
      authors: book.authors,
      discountId: book.discountId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  private createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      price: this.editForm.get(['price'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      imageURL: this.editForm.get(['imageURL'])!.value,
      language: this.editForm.get(['language'])!.value,
      format: this.editForm.get(['format'])!.value,
      paperBackQuantity: this.editForm.get(['paperBackQuantity'])!.value,
      publicationDate: this.editForm.get(['publicationDate'])!.value,
      isbn: this.editForm.get(['isbn'])!.value,
      pages: this.editForm.get(['pages'])!.value,
      otherDetails: this.editForm.get(['otherDetails'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
      authors: this.editForm.get(['authors'])!.value,
      discountId: this.editForm.get(['discountId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IAuthor[], option: IAuthor): IAuthor {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  /**
   * @author Emez
   * Asynchronous event triggered when the user tries to add an image file during book creation or update
   *
   * @param file the HTML input element of type file
   *
   * retrieves the chosen file of format .png, .jpg, or .jpeg, creates a relative firebase storage reference, and tries to upload the file to firebase
   * if successful, the downloadURL of the uploaded image is written to the imageURL input element for insertion/update in our database
   * otherwise the error message is written to console
   */
  async uploadToFirebase(file: HTMLInputElement): Promise<void> {
    // get file
    const img: File = file.files![0];

    // (re)create storage reference
    this.storageReference = this.firebaseStorage.ref('books/' + img.name);

    // upload file
    this.task = await this.storageReference.put(img).then(
      (snapshot: UploadTaskSnapshot) => {
        console.log('Firebase book image upload ' + snapshot.state);
        this.storageReference!.getDownloadURL().subscribe((url: Observable<string>) => {
          this.downloadURL = url;
          // (re)set the imageURL to the Firebase download URL
          this.editForm.get(['imageURL'])!.setValue(this.downloadURL);
        });
      },
      (err: Error) => {
        console.log('Error during upload: ' + err.message);
      }
    );
  }
}

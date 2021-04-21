import { Options } from '@angular-slider/ngx-slider';
import { HttpResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from 'app/entities/category/category.service';
import { ICategory } from 'app/shared/model/category.model';

@Component({
  selector: 'jhi-search-filter',
  templateUrl: './search-filter.component.html',
  styleUrls: ['./search-filter.component.scss'],
})
export class SearchFilterComponent implements OnInit {


  @Output() filterApplied = new EventEmitter<Map<string, any>>();

  categories : ICategory[] | undefined;
  isCollapsedFilter = true;
  isCollapsedSort   = true;

  selectedCategory = -1;
  priceMin = 0;
  priceMax = 250;
  rating = 0;

  curSort = '';


  filterForm = this.formBuilder.group({
    categoryId: ['', Validators.required],
    sortField: [ '' ],
  });

  sortForm = new FormGroup({
    sortField : new FormControl()
  });

  options_price: Options = {
    floor: 0,
    ceil: 250,
    vertical: false
  };



  constructor(private categoryService : CategoryService, 
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.categoryService.query().subscribe( (res : HttpResponse<ICategory[]>) => {
      if(res.body){
        this.categories = res.body;
        /*
        console.log("Categories : " );
        for(const cat of this.categories){
          console.log(" --> " + cat.name);
        }
        */
        this.updateForm();
      }
    });
   
  }

  updateForm() : void{
    this.filterForm.patchValue({
        categoryId: 1,
        sortField : "price.asc"
    }
    );
  }

  private createFilter() : Map<string, any>{

    const req = new Map()
    if(this.priceMin > 0){
      req.set('price.greaterOrEqualThan', this.priceMin);
    }
    if(this.priceMax >0){
      req.set('price.lessOrEqualThan', this.priceMax);
    }
    const selectedCat = this.selectedCategory;
    if(selectedCat >= 0){
      req.set('categoryId.equals', this.selectedCategory);
    }

    const selectedSort = this.curSort;
    if(selectedSort.length > 0){
      // console.log("SortField = " +selectedSort);
      req.set('sortField', selectedSort);
    }

    if(this.rating > 0){
      req.set('rating.greaterOrEqualThan', this.rating);
    }
    
    return req;
  }



  onChange(entry : string) :void {
    this.curSort = entry;
  }

  onApplyFilter(): void{
    /*

    console.log("CategoryId Selected(1) : " + this.selectedCategory);
    console.log("Apply Filters");
    console.log("MaxPrice : " + this.priceMax);
    console.log("MaxPrice : " + this.priceMin);
    console.log("CurSort : " + this.curSort);

    */
    this.filterApplied.emit(this.createFilter());
    this.isCollapsedFilter = true;
    this.isCollapsedSort = true;

  }

  onMinPriceChange(value : any) : void{
    this.options_price.floor = value;
  }

}

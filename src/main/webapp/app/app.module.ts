import { EcomBookstoreBookDetailsPageModule } from './book-details-page/book-details-page.module';
import { EcomBookStoreSearchPageModule } from './search-page/search-page.module';
import { EcomBookstoreBookCommentModule } from './book-comment/book-comment.module';
import { EcomBookStoreCheckoutModule } from './checkout/checkout.module';
import { EcomBookStorePageConfirmationModule } from './page-confirmation/page-confirmation.module';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { EcomBookstoreCoreModule } from 'app/core/core.module';
import { EcomBookstoreAppRoutingModule } from './app-routing.module';
import { EcomBookstoreHomeModule } from './home/home.module';
import { EcomBookstoreEntityModule } from './entities/entity.module';
import { EcomBookstoreOrderHistoryModule } from 'app/order-history/order-history.module';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ContactComponent } from './contact/contact.component';
import { CheckoutPageComponent } from './checkout-page/checkout-page.component';
import { EcomBookstoreShoppingCartModule } from './shopping-cart/shopping-cart.module';
import { EcomBookstoreIndexBodyModule } from 'app/index-body/index-body.module';
import { ReactiveFormsModule } from '@angular/forms';
import { AngularFireModule } from '@angular/fire';

export const firebaseConfig = {
  apiKey: 'AIzaSyD79slLhkP2Ok2sqwO2HRPEjBoyoE6EUH4',
  authDomain: 'ecom-bookstore.firebaseapp.com',
  databaseURL: 'https://ecom-bookstore.firebaseio.com',
  projectId: 'ecom-bookstore',
  storageBucket: 'ecom-bookstore.appspot.com',
  messagingSenderId: '945988539930',
  appId: '1:945988539930:web:f8e301fe6d61d7b74b23a3',
};

@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    EcomBookstoreSharedModule,
    EcomBookstoreCoreModule,
    EcomBookstoreHomeModule,
    EcomBookStorePageConfirmationModule,
    EcomBookStoreCheckoutModule,
    EcomBookStoreSearchPageModule,
    EcomBookstoreBookDetailsPageModule,
    EcomBookstoreShoppingCartModule,
    EcomBookstoreIndexBodyModule,
    EcomBookstoreBookCommentModule,
    EcomBookstoreOrderHistoryModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EcomBookstoreEntityModule,
    EcomBookstoreAppRoutingModule,
    AngularFireModule.initializeApp(firebaseConfig),
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    ContactComponent,
    CheckoutPageComponent,
  ],
  bootstrap: [MainComponent],
})
export class EcomBookstoreAppModule {}

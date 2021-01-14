import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CurrencyUpdatePage from './currency-update.page-object';

const expect = chai.expect;
export class CurrencyDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.currency.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-currency'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CurrencyComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('currency-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('currency');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCurrency() {
    await this.createButton.click();
    return new CurrencyUpdatePage();
  }

  async deleteCurrency() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const currencyDeleteDialog = new CurrencyDeleteDialog();
    await waitUntilDisplayed(currencyDeleteDialog.deleteModal);
    expect(await currencyDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.currency.delete.question/);
    await currencyDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(currencyDeleteDialog.deleteModal);

    expect(await isVisible(currencyDeleteDialog.deleteModal)).to.be.false;
  }
}

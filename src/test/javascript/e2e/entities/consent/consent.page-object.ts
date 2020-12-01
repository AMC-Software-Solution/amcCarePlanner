import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ConsentUpdatePage from './consent-update.page-object';

const expect = chai.expect;
export class ConsentDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.consent.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-consent'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ConsentComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('consent-heading'));
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
    await navBarPage.getEntityPage('consent');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateConsent() {
    await this.createButton.click();
    return new ConsentUpdatePage();
  }

  async deleteConsent() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const consentDeleteDialog = new ConsentDeleteDialog();
    await waitUntilDisplayed(consentDeleteDialog.deleteModal);
    expect(await consentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.consent.delete.question/);
    await consentDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(consentDeleteDialog.deleteModal);

    expect(await isVisible(consentDeleteDialog.deleteModal)).to.be.false;
  }
}

import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import EligibilityUpdatePage from './eligibility-update.page-object';

const expect = chai.expect;
export class EligibilityDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.eligibility.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-eligibility'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class EligibilityComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('eligibility-heading'));
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
    await navBarPage.getEntityPage('eligibility');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateEligibility() {
    await this.createButton.click();
    return new EligibilityUpdatePage();
  }

  async deleteEligibility() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const eligibilityDeleteDialog = new EligibilityDeleteDialog();
    await waitUntilDisplayed(eligibilityDeleteDialog.deleteModal);
    expect(await eligibilityDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.eligibility.delete.question/);
    await eligibilityDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(eligibilityDeleteDialog.deleteModal);

    expect(await isVisible(eligibilityDeleteDialog.deleteModal)).to.be.false;
  }
}

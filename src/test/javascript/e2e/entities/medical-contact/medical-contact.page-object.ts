import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import MedicalContactUpdatePage from './medical-contact-update.page-object';

const expect = chai.expect;
export class MedicalContactDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.medicalContact.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-medicalContact'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class MedicalContactComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('medical-contact-heading'));
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
    await navBarPage.getEntityPage('medical-contact');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateMedicalContact() {
    await this.createButton.click();
    return new MedicalContactUpdatePage();
  }

  async deleteMedicalContact() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const medicalContactDeleteDialog = new MedicalContactDeleteDialog();
    await waitUntilDisplayed(medicalContactDeleteDialog.deleteModal);
    expect(await medicalContactDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.medicalContact.delete.question/);
    await medicalContactDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(medicalContactDeleteDialog.deleteModal);

    expect(await isVisible(medicalContactDeleteDialog.deleteModal)).to.be.false;
  }
}

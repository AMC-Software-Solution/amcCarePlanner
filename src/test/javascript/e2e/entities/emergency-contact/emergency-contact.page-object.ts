import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import EmergencyContactUpdatePage from './emergency-contact-update.page-object';

const expect = chai.expect;
export class EmergencyContactDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.emergencyContact.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-emergencyContact'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class EmergencyContactComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('emergency-contact-heading'));
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
    await navBarPage.getEntityPage('emergency-contact');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateEmergencyContact() {
    await this.createButton.click();
    return new EmergencyContactUpdatePage();
  }

  async deleteEmergencyContact() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const emergencyContactDeleteDialog = new EmergencyContactDeleteDialog();
    await waitUntilDisplayed(emergencyContactDeleteDialog.deleteModal);
    expect(await emergencyContactDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.emergencyContact.delete.question/
    );
    await emergencyContactDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(emergencyContactDeleteDialog.deleteModal);

    expect(await isVisible(emergencyContactDeleteDialog.deleteModal)).to.be.false;
  }
}

import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import EmployeeAvailabilityUpdatePage from './employee-availability-update.page-object';

const expect = chai.expect;
export class EmployeeAvailabilityDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.employeeAvailability.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-employeeAvailability'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class EmployeeAvailabilityComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('employee-availability-heading'));
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
    await navBarPage.getEntityPage('employee-availability');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateEmployeeAvailability() {
    await this.createButton.click();
    return new EmployeeAvailabilityUpdatePage();
  }

  async deleteEmployeeAvailability() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const employeeAvailabilityDeleteDialog = new EmployeeAvailabilityDeleteDialog();
    await waitUntilDisplayed(employeeAvailabilityDeleteDialog.deleteModal);
    expect(await employeeAvailabilityDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.employeeAvailability.delete.question/
    );
    await employeeAvailabilityDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(employeeAvailabilityDeleteDialog.deleteModal);

    expect(await isVisible(employeeAvailabilityDeleteDialog.deleteModal)).to.be.false;
  }
}

import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CarerClientRelationUpdatePage from './carer-client-relation-update.page-object';

const expect = chai.expect;
export class CarerClientRelationDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.carerClientRelation.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-carerClientRelation'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CarerClientRelationComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('carer-client-relation-heading'));
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
    await navBarPage.getEntityPage('carer-client-relation');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCarerClientRelation() {
    await this.createButton.click();
    return new CarerClientRelationUpdatePage();
  }

  async deleteCarerClientRelation() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const carerClientRelationDeleteDialog = new CarerClientRelationDeleteDialog();
    await waitUntilDisplayed(carerClientRelationDeleteDialog.deleteModal);
    expect(await carerClientRelationDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.carerClientRelation.delete.question/
    );
    await carerClientRelationDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(carerClientRelationDeleteDialog.deleteModal);

    expect(await isVisible(carerClientRelationDeleteDialog.deleteModal)).to.be.false;
  }
}

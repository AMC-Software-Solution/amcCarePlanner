import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import SystemSettingUpdatePage from './system-setting-update.page-object';

const expect = chai.expect;
export class SystemSettingDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.systemSetting.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-systemSetting'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class SystemSettingComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('system-setting-heading'));
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
    await navBarPage.getEntityPage('system-setting');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateSystemSetting() {
    await this.createButton.click();
    return new SystemSettingUpdatePage();
  }

  async deleteSystemSetting() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const systemSettingDeleteDialog = new SystemSettingDeleteDialog();
    await waitUntilDisplayed(systemSettingDeleteDialog.deleteModal);
    expect(await systemSettingDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.systemSetting.delete.question/);
    await systemSettingDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(systemSettingDeleteDialog.deleteModal);

    expect(await isVisible(systemSettingDeleteDialog.deleteModal)).to.be.false;
  }
}

import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class SystemSettingUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.systemSetting.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  fieldNameInput: ElementFinder = element(by.css('input#system-setting-fieldName'));
  fieldValueInput: ElementFinder = element(by.css('input#system-setting-fieldValue'));
  defaultValueInput: ElementFinder = element(by.css('input#system-setting-defaultValue'));
  settingEnabledInput: ElementFinder = element(by.css('input#system-setting-settingEnabled'));
  createdDateInput: ElementFinder = element(by.css('input#system-setting-createdDate'));
  updatedDateInput: ElementFinder = element(by.css('input#system-setting-updatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#system-setting-clientId'));
  hasExtraDataInput: ElementFinder = element(by.css('input#system-setting-hasExtraData'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setFieldNameInput(fieldName) {
    await this.fieldNameInput.sendKeys(fieldName);
  }

  async getFieldNameInput() {
    return this.fieldNameInput.getAttribute('value');
  }

  async setFieldValueInput(fieldValue) {
    await this.fieldValueInput.sendKeys(fieldValue);
  }

  async getFieldValueInput() {
    return this.fieldValueInput.getAttribute('value');
  }

  async setDefaultValueInput(defaultValue) {
    await this.defaultValueInput.sendKeys(defaultValue);
  }

  async getDefaultValueInput() {
    return this.defaultValueInput.getAttribute('value');
  }

  getSettingEnabledInput() {
    return this.settingEnabledInput;
  }
  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return this.createdDateInput.getAttribute('value');
  }

  async setUpdatedDateInput(updatedDate) {
    await this.updatedDateInput.sendKeys(updatedDate);
  }

  async getUpdatedDateInput() {
    return this.updatedDateInput.getAttribute('value');
  }

  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
  }
  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setFieldNameInput('fieldName');
    expect(await this.getFieldNameInput()).to.match(/fieldName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setFieldValueInput('fieldValue');
    expect(await this.getFieldValueInput()).to.match(/fieldValue/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDefaultValueInput('defaultValue');
    expect(await this.getDefaultValueInput()).to.match(/defaultValue/);
    await waitUntilDisplayed(this.saveButton);
    const selectedSettingEnabled = await this.getSettingEnabledInput().isSelected();
    if (selectedSettingEnabled) {
      await this.getSettingEnabledInput().click();
      expect(await this.getSettingEnabledInput().isSelected()).to.be.false;
    } else {
      await this.getSettingEnabledInput().click();
      expect(await this.getSettingEnabledInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

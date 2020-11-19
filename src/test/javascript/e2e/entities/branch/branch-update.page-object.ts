import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class BranchUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.branch.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#branch-name'));
  addressInput: ElementFinder = element(by.css('input#branch-address'));
  telephoneInput: ElementFinder = element(by.css('input#branch-telephone'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#branch-lastUpdatedDate'));
  tenantSelect: ElementFinder = element(by.css('select#branch-tenant'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setTelephoneInput(telephone) {
    await this.telephoneInput.sendKeys(telephone);
  }

  async getTelephoneInput() {
    return this.telephoneInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async tenantSelectLastOption() {
    await this.tenantSelect.all(by.tagName('option')).last().click();
  }

  async tenantSelectOption(option) {
    await this.tenantSelect.sendKeys(option);
  }

  getTenantSelect() {
    return this.tenantSelect;
  }

  async getTenantSelectedOption() {
    return this.tenantSelect.element(by.css('option:checked')).getText();
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
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAddressInput('address');
    expect(await this.getAddressInput()).to.match(/address/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTelephoneInput('telephone');
    expect(await this.getTelephoneInput()).to.match(/telephone/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await this.tenantSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

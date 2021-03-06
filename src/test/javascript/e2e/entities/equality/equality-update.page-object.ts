import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EqualityUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.equality.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  genderSelect: ElementFinder = element(by.css('select#equality-gender'));
  maritalStatusSelect: ElementFinder = element(by.css('select#equality-maritalStatus'));
  religionSelect: ElementFinder = element(by.css('select#equality-religion'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#equality-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#equality-tenantId'));
  nationalitySelect: ElementFinder = element(by.css('select#equality-nationality'));
  serviceUserSelect: ElementFinder = element(by.css('select#equality-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setGenderSelect(gender) {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect() {
    return this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption() {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }
  async setMaritalStatusSelect(maritalStatus) {
    await this.maritalStatusSelect.sendKeys(maritalStatus);
  }

  async getMaritalStatusSelect() {
    return this.maritalStatusSelect.element(by.css('option:checked')).getText();
  }

  async maritalStatusSelectLastOption() {
    await this.maritalStatusSelect.all(by.tagName('option')).last().click();
  }
  async setReligionSelect(religion) {
    await this.religionSelect.sendKeys(religion);
  }

  async getReligionSelect() {
    return this.religionSelect.element(by.css('option:checked')).getText();
  }

  async religionSelectLastOption() {
    await this.religionSelect.all(by.tagName('option')).last().click();
  }
  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async setTenantIdInput(tenantId) {
    await this.tenantIdInput.sendKeys(tenantId);
  }

  async getTenantIdInput() {
    return this.tenantIdInput.getAttribute('value');
  }

  async nationalitySelectLastOption() {
    await this.nationalitySelect.all(by.tagName('option')).last().click();
  }

  async nationalitySelectOption(option) {
    await this.nationalitySelect.sendKeys(option);
  }

  getNationalitySelect() {
    return this.nationalitySelect;
  }

  async getNationalitySelectedOption() {
    return this.nationalitySelect.element(by.css('option:checked')).getText();
  }

  async serviceUserSelectLastOption() {
    await this.serviceUserSelect.all(by.tagName('option')).last().click();
  }

  async serviceUserSelectOption(option) {
    await this.serviceUserSelect.sendKeys(option);
  }

  getServiceUserSelect() {
    return this.serviceUserSelect;
  }

  async getServiceUserSelectedOption() {
    return this.serviceUserSelect.element(by.css('option:checked')).getText();
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
    await this.genderSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.maritalStatusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.religionSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.nationalitySelectLastOption();
    await this.serviceUserSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

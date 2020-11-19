import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class TenantUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.tenant.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tenantNameInput: ElementFinder = element(by.css('input#tenant-tenantName'));
  tenantDescriptionInput: ElementFinder = element(by.css('input#tenant-tenantDescription'));
  tenantLogoInput: ElementFinder = element(by.css('input#file_tenantLogo'));
  tenantLogoUrlInput: ElementFinder = element(by.css('input#tenant-tenantLogoUrl'));
  tenantContactNameInput: ElementFinder = element(by.css('input#tenant-tenantContactName'));
  tenantPhoneInput: ElementFinder = element(by.css('input#tenant-tenantPhone'));
  tenantEmailInput: ElementFinder = element(by.css('input#tenant-tenantEmail'));
  createdDateInput: ElementFinder = element(by.css('input#tenant-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#tenant-lastUpdatedDate'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTenantNameInput(tenantName) {
    await this.tenantNameInput.sendKeys(tenantName);
  }

  async getTenantNameInput() {
    return this.tenantNameInput.getAttribute('value');
  }

  async setTenantDescriptionInput(tenantDescription) {
    await this.tenantDescriptionInput.sendKeys(tenantDescription);
  }

  async getTenantDescriptionInput() {
    return this.tenantDescriptionInput.getAttribute('value');
  }

  async setTenantLogoInput(tenantLogo) {
    await this.tenantLogoInput.sendKeys(tenantLogo);
  }

  async getTenantLogoInput() {
    return this.tenantLogoInput.getAttribute('value');
  }

  async setTenantLogoUrlInput(tenantLogoUrl) {
    await this.tenantLogoUrlInput.sendKeys(tenantLogoUrl);
  }

  async getTenantLogoUrlInput() {
    return this.tenantLogoUrlInput.getAttribute('value');
  }

  async setTenantContactNameInput(tenantContactName) {
    await this.tenantContactNameInput.sendKeys(tenantContactName);
  }

  async getTenantContactNameInput() {
    return this.tenantContactNameInput.getAttribute('value');
  }

  async setTenantPhoneInput(tenantPhone) {
    await this.tenantPhoneInput.sendKeys(tenantPhone);
  }

  async getTenantPhoneInput() {
    return this.tenantPhoneInput.getAttribute('value');
  }

  async setTenantEmailInput(tenantEmail) {
    await this.tenantEmailInput.sendKeys(tenantEmail);
  }

  async getTenantEmailInput() {
    return this.tenantEmailInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return this.createdDateInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
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
    await this.setTenantNameInput('tenantName');
    expect(await this.getTenantNameInput()).to.match(/tenantName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantDescriptionInput('tenantDescription');
    expect(await this.getTenantDescriptionInput()).to.match(/tenantDescription/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantLogoInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantLogoUrlInput('tenantLogoUrl');
    expect(await this.getTenantLogoUrlInput()).to.match(/tenantLogoUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantContactNameInput('tenantContactName');
    expect(await this.getTenantContactNameInput()).to.match(/tenantContactName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantPhoneInput('tenantPhone');
    expect(await this.getTenantPhoneInput()).to.match(/tenantPhone/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantEmailInput('tenantEmail');
    expect(await this.getTenantEmailInput()).to.match(/tenantEmail/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

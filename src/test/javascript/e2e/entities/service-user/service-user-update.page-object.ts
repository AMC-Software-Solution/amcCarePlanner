import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class ServiceUserUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.serviceUser.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleSelect: ElementFinder = element(by.css('select#service-user-title'));
  firstNameInput: ElementFinder = element(by.css('input#service-user-firstName'));
  middleNameInput: ElementFinder = element(by.css('input#service-user-middleName'));
  lastNameInput: ElementFinder = element(by.css('input#service-user-lastName'));
  preferredNameInput: ElementFinder = element(by.css('input#service-user-preferredName'));
  emailInput: ElementFinder = element(by.css('input#service-user-email'));
  serviceUserCodeInput: ElementFinder = element(by.css('input#service-user-serviceUserCode'));
  dateOfBirthInput: ElementFinder = element(by.css('input#service-user-dateOfBirth'));
  lastVisitDateInput: ElementFinder = element(by.css('input#service-user-lastVisitDate'));
  startDateInput: ElementFinder = element(by.css('input#service-user-startDate'));
  supportTypeSelect: ElementFinder = element(by.css('select#service-user-supportType'));
  serviceUserCategorySelect: ElementFinder = element(by.css('select#service-user-serviceUserCategory'));
  vulnerabilitySelect: ElementFinder = element(by.css('select#service-user-vulnerability'));
  servicePrioritySelect: ElementFinder = element(by.css('select#service-user-servicePriority'));
  sourceSelect: ElementFinder = element(by.css('select#service-user-source'));
  statusSelect: ElementFinder = element(by.css('select#service-user-status'));
  firstLanguageInput: ElementFinder = element(by.css('input#service-user-firstLanguage'));
  interpreterRequiredInput: ElementFinder = element(by.css('input#service-user-interpreterRequired'));
  activatedDateInput: ElementFinder = element(by.css('input#service-user-activatedDate'));
  profilePhotoInput: ElementFinder = element(by.css('input#file_profilePhoto'));
  profilePhotoUrlInput: ElementFinder = element(by.css('input#service-user-profilePhotoUrl'));
  lastRecordedHeightInput: ElementFinder = element(by.css('input#service-user-lastRecordedHeight'));
  lastRecordedWeightInput: ElementFinder = element(by.css('input#service-user-lastRecordedWeight'));
  hasMedicalConditionInput: ElementFinder = element(by.css('input#service-user-hasMedicalCondition'));
  medicalConditionSummaryInput: ElementFinder = element(by.css('input#service-user-medicalConditionSummary'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#service-user-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#service-user-clientId'));
  userSelect: ElementFinder = element(by.css('select#service-user-user'));
  branchSelect: ElementFinder = element(by.css('select#service-user-branch'));
  registeredBySelect: ElementFinder = element(by.css('select#service-user-registeredBy'));
  activatedBySelect: ElementFinder = element(by.css('select#service-user-activatedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleSelect(title) {
    await this.titleSelect.sendKeys(title);
  }

  async getTitleSelect() {
    return this.titleSelect.element(by.css('option:checked')).getText();
  }

  async titleSelectLastOption() {
    await this.titleSelect.all(by.tagName('option')).last().click();
  }
  async setFirstNameInput(firstName) {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput() {
    return this.firstNameInput.getAttribute('value');
  }

  async setMiddleNameInput(middleName) {
    await this.middleNameInput.sendKeys(middleName);
  }

  async getMiddleNameInput() {
    return this.middleNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName) {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput() {
    return this.lastNameInput.getAttribute('value');
  }

  async setPreferredNameInput(preferredName) {
    await this.preferredNameInput.sendKeys(preferredName);
  }

  async getPreferredNameInput() {
    return this.preferredNameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return this.emailInput.getAttribute('value');
  }

  async setServiceUserCodeInput(serviceUserCode) {
    await this.serviceUserCodeInput.sendKeys(serviceUserCode);
  }

  async getServiceUserCodeInput() {
    return this.serviceUserCodeInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth) {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput() {
    return this.dateOfBirthInput.getAttribute('value');
  }

  async setLastVisitDateInput(lastVisitDate) {
    await this.lastVisitDateInput.sendKeys(lastVisitDate);
  }

  async getLastVisitDateInput() {
    return this.lastVisitDateInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return this.startDateInput.getAttribute('value');
  }

  async setSupportTypeSelect(supportType) {
    await this.supportTypeSelect.sendKeys(supportType);
  }

  async getSupportTypeSelect() {
    return this.supportTypeSelect.element(by.css('option:checked')).getText();
  }

  async supportTypeSelectLastOption() {
    await this.supportTypeSelect.all(by.tagName('option')).last().click();
  }
  async setServiceUserCategorySelect(serviceUserCategory) {
    await this.serviceUserCategorySelect.sendKeys(serviceUserCategory);
  }

  async getServiceUserCategorySelect() {
    return this.serviceUserCategorySelect.element(by.css('option:checked')).getText();
  }

  async serviceUserCategorySelectLastOption() {
    await this.serviceUserCategorySelect.all(by.tagName('option')).last().click();
  }
  async setVulnerabilitySelect(vulnerability) {
    await this.vulnerabilitySelect.sendKeys(vulnerability);
  }

  async getVulnerabilitySelect() {
    return this.vulnerabilitySelect.element(by.css('option:checked')).getText();
  }

  async vulnerabilitySelectLastOption() {
    await this.vulnerabilitySelect.all(by.tagName('option')).last().click();
  }
  async setServicePrioritySelect(servicePriority) {
    await this.servicePrioritySelect.sendKeys(servicePriority);
  }

  async getServicePrioritySelect() {
    return this.servicePrioritySelect.element(by.css('option:checked')).getText();
  }

  async servicePrioritySelectLastOption() {
    await this.servicePrioritySelect.all(by.tagName('option')).last().click();
  }
  async setSourceSelect(source) {
    await this.sourceSelect.sendKeys(source);
  }

  async getSourceSelect() {
    return this.sourceSelect.element(by.css('option:checked')).getText();
  }

  async sourceSelectLastOption() {
    await this.sourceSelect.all(by.tagName('option')).last().click();
  }
  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }
  async setFirstLanguageInput(firstLanguage) {
    await this.firstLanguageInput.sendKeys(firstLanguage);
  }

  async getFirstLanguageInput() {
    return this.firstLanguageInput.getAttribute('value');
  }

  getInterpreterRequiredInput() {
    return this.interpreterRequiredInput;
  }
  async setActivatedDateInput(activatedDate) {
    await this.activatedDateInput.sendKeys(activatedDate);
  }

  async getActivatedDateInput() {
    return this.activatedDateInput.getAttribute('value');
  }

  async setProfilePhotoInput(profilePhoto) {
    await this.profilePhotoInput.sendKeys(profilePhoto);
  }

  async getProfilePhotoInput() {
    return this.profilePhotoInput.getAttribute('value');
  }

  async setProfilePhotoUrlInput(profilePhotoUrl) {
    await this.profilePhotoUrlInput.sendKeys(profilePhotoUrl);
  }

  async getProfilePhotoUrlInput() {
    return this.profilePhotoUrlInput.getAttribute('value');
  }

  async setLastRecordedHeightInput(lastRecordedHeight) {
    await this.lastRecordedHeightInput.sendKeys(lastRecordedHeight);
  }

  async getLastRecordedHeightInput() {
    return this.lastRecordedHeightInput.getAttribute('value');
  }

  async setLastRecordedWeightInput(lastRecordedWeight) {
    await this.lastRecordedWeightInput.sendKeys(lastRecordedWeight);
  }

  async getLastRecordedWeightInput() {
    return this.lastRecordedWeightInput.getAttribute('value');
  }

  getHasMedicalConditionInput() {
    return this.hasMedicalConditionInput;
  }
  async setMedicalConditionSummaryInput(medicalConditionSummary) {
    await this.medicalConditionSummaryInput.sendKeys(medicalConditionSummary);
  }

  async getMedicalConditionSummaryInput() {
    return this.medicalConditionSummaryInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  async userSelectLastOption() {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect() {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return this.userSelect.element(by.css('option:checked')).getText();
  }

  async branchSelectLastOption() {
    await this.branchSelect.all(by.tagName('option')).last().click();
  }

  async branchSelectOption(option) {
    await this.branchSelect.sendKeys(option);
  }

  getBranchSelect() {
    return this.branchSelect;
  }

  async getBranchSelectedOption() {
    return this.branchSelect.element(by.css('option:checked')).getText();
  }

  async registeredBySelectLastOption() {
    await this.registeredBySelect.all(by.tagName('option')).last().click();
  }

  async registeredBySelectOption(option) {
    await this.registeredBySelect.sendKeys(option);
  }

  getRegisteredBySelect() {
    return this.registeredBySelect;
  }

  async getRegisteredBySelectedOption() {
    return this.registeredBySelect.element(by.css('option:checked')).getText();
  }

  async activatedBySelectLastOption() {
    await this.activatedBySelect.all(by.tagName('option')).last().click();
  }

  async activatedBySelectOption(option) {
    await this.activatedBySelect.sendKeys(option);
  }

  getActivatedBySelect() {
    return this.activatedBySelect;
  }

  async getActivatedBySelectedOption() {
    return this.activatedBySelect.element(by.css('option:checked')).getText();
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
    await this.titleSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setFirstNameInput('firstName');
    expect(await this.getFirstNameInput()).to.match(/firstName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setMiddleNameInput('middleName');
    expect(await this.getMiddleNameInput()).to.match(/middleName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastNameInput('lastName');
    expect(await this.getLastNameInput()).to.match(/lastName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPreferredNameInput('preferredName');
    expect(await this.getPreferredNameInput()).to.match(/preferredName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEmailInput('email');
    expect(await this.getEmailInput()).to.match(/email/);
    await waitUntilDisplayed(this.saveButton);
    await this.setServiceUserCodeInput('serviceUserCode');
    expect(await this.getServiceUserCodeInput()).to.match(/serviceUserCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDateOfBirthInput('01-01-2001');
    expect(await this.getDateOfBirthInput()).to.eq('2001-01-01');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastVisitDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastVisitDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getStartDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.supportTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.serviceUserCategorySelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.vulnerabilitySelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.servicePrioritySelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.sourceSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.statusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setFirstLanguageInput('firstLanguage');
    expect(await this.getFirstLanguageInput()).to.match(/firstLanguage/);
    await waitUntilDisplayed(this.saveButton);
    const selectedInterpreterRequired = await this.getInterpreterRequiredInput().isSelected();
    if (selectedInterpreterRequired) {
      await this.getInterpreterRequiredInput().click();
      expect(await this.getInterpreterRequiredInput().isSelected()).to.be.false;
    } else {
      await this.getInterpreterRequiredInput().click();
      expect(await this.getInterpreterRequiredInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setActivatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getActivatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setProfilePhotoInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setProfilePhotoUrlInput('profilePhotoUrl');
    expect(await this.getProfilePhotoUrlInput()).to.match(/profilePhotoUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastRecordedHeightInput('lastRecordedHeight');
    expect(await this.getLastRecordedHeightInput()).to.match(/lastRecordedHeight/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastRecordedWeightInput('lastRecordedWeight');
    expect(await this.getLastRecordedWeightInput()).to.match(/lastRecordedWeight/);
    await waitUntilDisplayed(this.saveButton);
    const selectedHasMedicalCondition = await this.getHasMedicalConditionInput().isSelected();
    if (selectedHasMedicalCondition) {
      await this.getHasMedicalConditionInput().click();
      expect(await this.getHasMedicalConditionInput().isSelected()).to.be.false;
    } else {
      await this.getHasMedicalConditionInput().click();
      expect(await this.getHasMedicalConditionInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setMedicalConditionSummaryInput('medicalConditionSummary');
    expect(await this.getMedicalConditionSummaryInput()).to.match(/medicalConditionSummary/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.userSelectLastOption();
    await this.branchSelectLastOption();
    await this.registeredBySelectLastOption();
    await this.activatedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EmployeeAvailabilityUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.employeeAvailability.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  isAvailableForWorkInput: ElementFinder = element(by.css('input#employee-availability-isAvailableForWork'));
  minimumHoursPerWeekInput: ElementFinder = element(by.css('input#employee-availability-minimumHoursPerWeek'));
  maximumHoursPerWeekInput: ElementFinder = element(by.css('input#employee-availability-maximumHoursPerWeek'));
  leastPreferredShiftSelect: ElementFinder = element(by.css('select#employee-availability-leastPreferredShift'));
  employeeSelect: ElementFinder = element(by.css('select#employee-availability-employee'));

  getPageTitle() {
    return this.pageTitle;
  }

  getIsAvailableForWorkInput() {
    return this.isAvailableForWorkInput;
  }
  async setMinimumHoursPerWeekInput(minimumHoursPerWeek) {
    await this.minimumHoursPerWeekInput.sendKeys(minimumHoursPerWeek);
  }

  async getMinimumHoursPerWeekInput() {
    return this.minimumHoursPerWeekInput.getAttribute('value');
  }

  async setMaximumHoursPerWeekInput(maximumHoursPerWeek) {
    await this.maximumHoursPerWeekInput.sendKeys(maximumHoursPerWeek);
  }

  async getMaximumHoursPerWeekInput() {
    return this.maximumHoursPerWeekInput.getAttribute('value');
  }

  async setLeastPreferredShiftSelect(leastPreferredShift) {
    await this.leastPreferredShiftSelect.sendKeys(leastPreferredShift);
  }

  async getLeastPreferredShiftSelect() {
    return this.leastPreferredShiftSelect.element(by.css('option:checked')).getText();
  }

  async leastPreferredShiftSelectLastOption() {
    await this.leastPreferredShiftSelect.all(by.tagName('option')).last().click();
  }
  async employeeSelectLastOption() {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option) {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect() {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption() {
    return this.employeeSelect.element(by.css('option:checked')).getText();
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
    const selectedIsAvailableForWork = await this.getIsAvailableForWorkInput().isSelected();
    if (selectedIsAvailableForWork) {
      await this.getIsAvailableForWorkInput().click();
      expect(await this.getIsAvailableForWorkInput().isSelected()).to.be.false;
    } else {
      await this.getIsAvailableForWorkInput().click();
      expect(await this.getIsAvailableForWorkInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setMinimumHoursPerWeekInput('5');
    expect(await this.getMinimumHoursPerWeekInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMaximumHoursPerWeekInput('5');
    expect(await this.getMaximumHoursPerWeekInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.leastPreferredShiftSelectLastOption();
    await this.employeeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

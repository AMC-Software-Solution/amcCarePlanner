import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EmployeeAvailabilityComponentsPage from './employee-availability.page-object';
import EmployeeAvailabilityUpdatePage from './employee-availability-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('EmployeeAvailability e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeAvailabilityComponentsPage: EmployeeAvailabilityComponentsPage;
  let employeeAvailabilityUpdatePage: EmployeeAvailabilityUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    employeeAvailabilityComponentsPage = new EmployeeAvailabilityComponentsPage();
    employeeAvailabilityComponentsPage = await employeeAvailabilityComponentsPage.goToPage(navBarPage);
  });

  it('should load EmployeeAvailabilities', async () => {
    expect(await employeeAvailabilityComponentsPage.title.getText()).to.match(/Employee Availabilities/);
    expect(await employeeAvailabilityComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete EmployeeAvailabilities', async () => {
    const beforeRecordsCount = (await isVisible(employeeAvailabilityComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(employeeAvailabilityComponentsPage.table);
    employeeAvailabilityUpdatePage = await employeeAvailabilityComponentsPage.goToCreateEmployeeAvailability();
    await employeeAvailabilityUpdatePage.enterData();

    expect(await employeeAvailabilityComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(employeeAvailabilityComponentsPage.table);
    await waitUntilCount(employeeAvailabilityComponentsPage.records, beforeRecordsCount + 1);
    expect(await employeeAvailabilityComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await employeeAvailabilityComponentsPage.deleteEmployeeAvailability();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(employeeAvailabilityComponentsPage.records, beforeRecordsCount);
      expect(await employeeAvailabilityComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(employeeAvailabilityComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

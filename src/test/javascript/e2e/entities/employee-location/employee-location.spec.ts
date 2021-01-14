import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EmployeeLocationComponentsPage from './employee-location.page-object';
import EmployeeLocationUpdatePage from './employee-location-update.page-object';
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

describe('EmployeeLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeLocationComponentsPage: EmployeeLocationComponentsPage;
  let employeeLocationUpdatePage: EmployeeLocationUpdatePage;

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
    employeeLocationComponentsPage = new EmployeeLocationComponentsPage();
    employeeLocationComponentsPage = await employeeLocationComponentsPage.goToPage(navBarPage);
  });

  it('should load EmployeeLocations', async () => {
    expect(await employeeLocationComponentsPage.title.getText()).to.match(/Employee Locations/);
    expect(await employeeLocationComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete EmployeeLocations', async () => {
    const beforeRecordsCount = (await isVisible(employeeLocationComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(employeeLocationComponentsPage.table);
    employeeLocationUpdatePage = await employeeLocationComponentsPage.goToCreateEmployeeLocation();
    await employeeLocationUpdatePage.enterData();

    expect(await employeeLocationComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(employeeLocationComponentsPage.table);
    await waitUntilCount(employeeLocationComponentsPage.records, beforeRecordsCount + 1);
    expect(await employeeLocationComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await employeeLocationComponentsPage.deleteEmployeeLocation();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(employeeLocationComponentsPage.records, beforeRecordsCount);
      expect(await employeeLocationComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(employeeLocationComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

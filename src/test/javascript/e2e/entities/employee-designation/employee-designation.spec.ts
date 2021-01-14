import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EmployeeDesignationComponentsPage from './employee-designation.page-object';
import EmployeeDesignationUpdatePage from './employee-designation-update.page-object';
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

describe('EmployeeDesignation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeDesignationComponentsPage: EmployeeDesignationComponentsPage;
  let employeeDesignationUpdatePage: EmployeeDesignationUpdatePage;

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
    employeeDesignationComponentsPage = new EmployeeDesignationComponentsPage();
    employeeDesignationComponentsPage = await employeeDesignationComponentsPage.goToPage(navBarPage);
  });

  it('should load EmployeeDesignations', async () => {
    expect(await employeeDesignationComponentsPage.title.getText()).to.match(/Employee Designations/);
    expect(await employeeDesignationComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete EmployeeDesignations', async () => {
    const beforeRecordsCount = (await isVisible(employeeDesignationComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(employeeDesignationComponentsPage.table);
    employeeDesignationUpdatePage = await employeeDesignationComponentsPage.goToCreateEmployeeDesignation();
    await employeeDesignationUpdatePage.enterData();

    expect(await employeeDesignationComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(employeeDesignationComponentsPage.table);
    await waitUntilCount(employeeDesignationComponentsPage.records, beforeRecordsCount + 1);
    expect(await employeeDesignationComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await employeeDesignationComponentsPage.deleteEmployeeDesignation();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(employeeDesignationComponentsPage.records, beforeRecordsCount);
      expect(await employeeDesignationComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(employeeDesignationComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

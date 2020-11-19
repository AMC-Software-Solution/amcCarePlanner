import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EmployeeDocumentComponentsPage from './employee-document.page-object';
import EmployeeDocumentUpdatePage from './employee-document-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('EmployeeDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeDocumentComponentsPage: EmployeeDocumentComponentsPage;
  let employeeDocumentUpdatePage: EmployeeDocumentUpdatePage;

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
    employeeDocumentComponentsPage = new EmployeeDocumentComponentsPage();
    employeeDocumentComponentsPage = await employeeDocumentComponentsPage.goToPage(navBarPage);
  });

  it('should load EmployeeDocuments', async () => {
    expect(await employeeDocumentComponentsPage.title.getText()).to.match(/Employee Documents/);
    expect(await employeeDocumentComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete EmployeeDocuments', async () => {
    const beforeRecordsCount = (await isVisible(employeeDocumentComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(employeeDocumentComponentsPage.table);
    employeeDocumentUpdatePage = await employeeDocumentComponentsPage.goToCreateEmployeeDocument();
    await employeeDocumentUpdatePage.enterData();

    expect(await employeeDocumentComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(employeeDocumentComponentsPage.table);
    await waitUntilCount(employeeDocumentComponentsPage.records, beforeRecordsCount + 1);
    expect(await employeeDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await employeeDocumentComponentsPage.deleteEmployeeDocument();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(employeeDocumentComponentsPage.records, beforeRecordsCount);
      expect(await employeeDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(employeeDocumentComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

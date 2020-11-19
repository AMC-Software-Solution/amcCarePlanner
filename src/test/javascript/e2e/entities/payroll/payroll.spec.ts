import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PayrollComponentsPage from './payroll.page-object';
import PayrollUpdatePage from './payroll-update.page-object';
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

describe('Payroll e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let payrollComponentsPage: PayrollComponentsPage;
  let payrollUpdatePage: PayrollUpdatePage;

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
    payrollComponentsPage = new PayrollComponentsPage();
    payrollComponentsPage = await payrollComponentsPage.goToPage(navBarPage);
  });

  it('should load Payrolls', async () => {
    expect(await payrollComponentsPage.title.getText()).to.match(/Payrolls/);
    expect(await payrollComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Payrolls', async () => {
    const beforeRecordsCount = (await isVisible(payrollComponentsPage.noRecords)) ? 0 : await getRecordsCount(payrollComponentsPage.table);
    payrollUpdatePage = await payrollComponentsPage.goToCreatePayroll();
    await payrollUpdatePage.enterData();

    expect(await payrollComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(payrollComponentsPage.table);
    await waitUntilCount(payrollComponentsPage.records, beforeRecordsCount + 1);
    expect(await payrollComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await payrollComponentsPage.deletePayroll();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(payrollComponentsPage.records, beforeRecordsCount);
      expect(await payrollComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(payrollComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

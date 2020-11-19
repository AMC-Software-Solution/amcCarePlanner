import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import BranchComponentsPage from './branch.page-object';
import BranchUpdatePage from './branch-update.page-object';
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

describe('Branch e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let branchComponentsPage: BranchComponentsPage;
  let branchUpdatePage: BranchUpdatePage;

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
    branchComponentsPage = new BranchComponentsPage();
    branchComponentsPage = await branchComponentsPage.goToPage(navBarPage);
  });

  it('should load Branches', async () => {
    expect(await branchComponentsPage.title.getText()).to.match(/Branches/);
    expect(await branchComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Branches', async () => {
    const beforeRecordsCount = (await isVisible(branchComponentsPage.noRecords)) ? 0 : await getRecordsCount(branchComponentsPage.table);
    branchUpdatePage = await branchComponentsPage.goToCreateBranch();
    await branchUpdatePage.enterData();

    expect(await branchComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(branchComponentsPage.table);
    await waitUntilCount(branchComponentsPage.records, beforeRecordsCount + 1);
    expect(await branchComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await branchComponentsPage.deleteBranch();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(branchComponentsPage.records, beforeRecordsCount);
      expect(await branchComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(branchComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

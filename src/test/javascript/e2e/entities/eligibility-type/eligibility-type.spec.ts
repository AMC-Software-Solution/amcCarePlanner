import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EligibilityTypeComponentsPage from './eligibility-type.page-object';
import EligibilityTypeUpdatePage from './eligibility-type-update.page-object';
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

describe('EligibilityType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let eligibilityTypeComponentsPage: EligibilityTypeComponentsPage;
  let eligibilityTypeUpdatePage: EligibilityTypeUpdatePage;

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
    eligibilityTypeComponentsPage = new EligibilityTypeComponentsPage();
    eligibilityTypeComponentsPage = await eligibilityTypeComponentsPage.goToPage(navBarPage);
  });

  it('should load EligibilityTypes', async () => {
    expect(await eligibilityTypeComponentsPage.title.getText()).to.match(/Eligibility Types/);
    expect(await eligibilityTypeComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete EligibilityTypes', async () => {
    const beforeRecordsCount = (await isVisible(eligibilityTypeComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(eligibilityTypeComponentsPage.table);
    eligibilityTypeUpdatePage = await eligibilityTypeComponentsPage.goToCreateEligibilityType();
    await eligibilityTypeUpdatePage.enterData();

    expect(await eligibilityTypeComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(eligibilityTypeComponentsPage.table);
    await waitUntilCount(eligibilityTypeComponentsPage.records, beforeRecordsCount + 1);
    expect(await eligibilityTypeComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await eligibilityTypeComponentsPage.deleteEligibilityType();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(eligibilityTypeComponentsPage.records, beforeRecordsCount);
      expect(await eligibilityTypeComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(eligibilityTypeComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

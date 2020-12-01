import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EligibilityComponentsPage from './eligibility.page-object';
import EligibilityUpdatePage from './eligibility-update.page-object';
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

describe('Eligibility e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let eligibilityComponentsPage: EligibilityComponentsPage;
  let eligibilityUpdatePage: EligibilityUpdatePage;

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
    eligibilityComponentsPage = new EligibilityComponentsPage();
    eligibilityComponentsPage = await eligibilityComponentsPage.goToPage(navBarPage);
  });

  it('should load Eligibilities', async () => {
    expect(await eligibilityComponentsPage.title.getText()).to.match(/Eligibilities/);
    expect(await eligibilityComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Eligibilities', async () => {
    const beforeRecordsCount = (await isVisible(eligibilityComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(eligibilityComponentsPage.table);
    eligibilityUpdatePage = await eligibilityComponentsPage.goToCreateEligibility();
    await eligibilityUpdatePage.enterData();

    expect(await eligibilityComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(eligibilityComponentsPage.table);
    await waitUntilCount(eligibilityComponentsPage.records, beforeRecordsCount + 1);
    expect(await eligibilityComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await eligibilityComponentsPage.deleteEligibility();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(eligibilityComponentsPage.records, beforeRecordsCount);
      expect(await eligibilityComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(eligibilityComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

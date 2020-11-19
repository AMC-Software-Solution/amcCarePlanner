import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CarerClientRelationComponentsPage from './carer-client-relation.page-object';
import CarerClientRelationUpdatePage from './carer-client-relation-update.page-object';
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

describe('CarerClientRelation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let carerClientRelationComponentsPage: CarerClientRelationComponentsPage;
  let carerClientRelationUpdatePage: CarerClientRelationUpdatePage;

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
    carerClientRelationComponentsPage = new CarerClientRelationComponentsPage();
    carerClientRelationComponentsPage = await carerClientRelationComponentsPage.goToPage(navBarPage);
  });

  it('should load CarerClientRelations', async () => {
    expect(await carerClientRelationComponentsPage.title.getText()).to.match(/Carer Client Relations/);
    expect(await carerClientRelationComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete CarerClientRelations', async () => {
    const beforeRecordsCount = (await isVisible(carerClientRelationComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(carerClientRelationComponentsPage.table);
    carerClientRelationUpdatePage = await carerClientRelationComponentsPage.goToCreateCarerClientRelation();
    await carerClientRelationUpdatePage.enterData();

    expect(await carerClientRelationComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(carerClientRelationComponentsPage.table);
    await waitUntilCount(carerClientRelationComponentsPage.records, beforeRecordsCount + 1);
    expect(await carerClientRelationComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await carerClientRelationComponentsPage.deleteCarerClientRelation();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(carerClientRelationComponentsPage.records, beforeRecordsCount);
      expect(await carerClientRelationComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(carerClientRelationComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

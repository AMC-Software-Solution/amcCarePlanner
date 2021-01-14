import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/client">
      <Translate contentKey="global.menu.entities.client" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/branch">
      <Translate contentKey="global.menu.entities.branch" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-user">
      <Translate contentKey="global.menu.entities.serviceUser" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/medical-contact">
      <Translate contentKey="global.menu.entities.medicalContact" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-user-contact">
      <Translate contentKey="global.menu.entities.serviceUserContact" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/emergency-contact">
      <Translate contentKey="global.menu.entities.emergencyContact" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-user-location">
      <Translate contentKey="global.menu.entities.serviceUserLocation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/power-of-attorney">
      <Translate contentKey="global.menu.entities.powerOfAttorney" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/consent">
      <Translate contentKey="global.menu.entities.consent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/access">
      <Translate contentKey="global.menu.entities.access" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/equality">
      <Translate contentKey="global.menu.entities.equality" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/question-type">
      <Translate contentKey="global.menu.entities.questionType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/question">
      <Translate contentKey="global.menu.entities.question" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/answer">
      <Translate contentKey="global.menu.entities.answer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-designation">
      <Translate contentKey="global.menu.entities.employeeDesignation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee">
      <Translate contentKey="global.menu.entities.employee" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-location">
      <Translate contentKey="global.menu.entities.employeeLocation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-availability">
      <Translate contentKey="global.menu.entities.employeeAvailability" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/relationship-type">
      <Translate contentKey="global.menu.entities.relationshipType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/carer-service-user-relation">
      <Translate contentKey="global.menu.entities.carerServiceUserRelation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/disability-type">
      <Translate contentKey="global.menu.entities.disabilityType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/disability">
      <Translate contentKey="global.menu.entities.disability" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/eligibility-type">
      <Translate contentKey="global.menu.entities.eligibilityType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/eligibility">
      <Translate contentKey="global.menu.entities.eligibility" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/travel">
      <Translate contentKey="global.menu.entities.travel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payroll">
      <Translate contentKey="global.menu.entities.payroll" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-holiday">
      <Translate contentKey="global.menu.entities.employeeHoliday" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/servce-user-document">
      <Translate contentKey="global.menu.entities.servceUserDocument" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/terminal-device">
      <Translate contentKey="global.menu.entities.terminalDevice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-document">
      <Translate contentKey="global.menu.entities.employeeDocument" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/communication">
      <Translate contentKey="global.menu.entities.communication" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/document-type">
      <Translate contentKey="global.menu.entities.documentType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/notification">
      <Translate contentKey="global.menu.entities.notification" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      <Translate contentKey="global.menu.entities.country" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/task">
      <Translate contentKey="global.menu.entities.task" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-user-event">
      <Translate contentKey="global.menu.entities.serviceUserEvent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/currency">
      <Translate contentKey="global.menu.entities.currency" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-order">
      <Translate contentKey="global.menu.entities.serviceOrder" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/invoice">
      <Translate contentKey="global.menu.entities.invoice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/timesheet">
      <Translate contentKey="global.menu.entities.timesheet" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/client-document">
      <Translate contentKey="global.menu.entities.clientDocument" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/system-events-history">
      <Translate contentKey="global.menu.entities.systemEventsHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/system-setting">
      <Translate contentKey="global.menu.entities.systemSetting" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/extra-data">
      <Translate contentKey="global.menu.entities.extraData" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

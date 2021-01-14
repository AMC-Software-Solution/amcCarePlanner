import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISystemSetting, defaultValue } from 'app/shared/model/system-setting.model';

export const ACTION_TYPES = {
  FETCH_SYSTEMSETTING_LIST: 'systemSetting/FETCH_SYSTEMSETTING_LIST',
  FETCH_SYSTEMSETTING: 'systemSetting/FETCH_SYSTEMSETTING',
  CREATE_SYSTEMSETTING: 'systemSetting/CREATE_SYSTEMSETTING',
  UPDATE_SYSTEMSETTING: 'systemSetting/UPDATE_SYSTEMSETTING',
  DELETE_SYSTEMSETTING: 'systemSetting/DELETE_SYSTEMSETTING',
  RESET: 'systemSetting/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISystemSetting>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SystemSettingState = Readonly<typeof initialState>;

// Reducer

export default (state: SystemSettingState = initialState, action): SystemSettingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMSETTING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMSETTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SYSTEMSETTING):
    case REQUEST(ACTION_TYPES.UPDATE_SYSTEMSETTING):
    case REQUEST(ACTION_TYPES.DELETE_SYSTEMSETTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMSETTING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMSETTING):
    case FAILURE(ACTION_TYPES.CREATE_SYSTEMSETTING):
    case FAILURE(ACTION_TYPES.UPDATE_SYSTEMSETTING):
    case FAILURE(ACTION_TYPES.DELETE_SYSTEMSETTING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMSETTING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMSETTING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SYSTEMSETTING):
    case SUCCESS(ACTION_TYPES.UPDATE_SYSTEMSETTING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SYSTEMSETTING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/v1';

// Actions

export const getEntities: ICrudGetAllAction<ISystemSetting> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-system-settings-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMSETTING_LIST,
    payload: axios.get<ISystemSetting>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISystemSetting> = id => {
  const requestUrl = `${apiUrl}/get-system-setting-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMSETTING,
    payload: axios.get<ISystemSetting>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISystemSetting> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SYSTEMSETTING,
    payload: axios.post(apiUrl + '/create-system-setting-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISystemSetting> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SYSTEMSETTING,
    payload: axios.put(apiUrl + '/update-system-setting-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISystemSetting> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-system-setting-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SYSTEMSETTING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

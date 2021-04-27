package com.example.thovin.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.Utility;
import com.example.thovin.models.user.AddressModel;
import com.example.thovin.models.user.UserModel;
import com.example.thovin.services.HttpClient;
import com.example.thovin.models.auth.LoginModel;
import com.example.thovin.models.auth.RegisterModel;
import com.example.thovin.models.auth.AuthResult;
import com.example.thovin.services.AuthServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private static final UserViewModelRepository userViewModelRepository = UserViewModelRepository.getInstance();
    private static final AuthServices authServices = HttpClient.getInstance().getAuthServices();

    private MutableLiveData<AuthResult> currentUser;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);


    // --- GETTER && SETTERS
    public MutableLiveData<AuthResult> getCurrentUser() {
        if (currentUser == null) currentUser = userViewModelRepository.getCurrentUser();
        return currentUser;
    }

    public void setCurrentUser(AuthResult value) {
        userViewModelRepository.setCurrentUser(value);
        currentUser.setValue(value);

    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean value) {
        isLoading.setValue(value);
    }


    /**
     * Login
     */
    public void login(LoginModel loginModel) {
        setIsLoading(true);

        authServices.login(loginModel).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                setIsLoading(false);

                AuthResult result = new AuthResult();

                if (response.isSuccessful()) {
                    result = response.body();
                    result.setSuccess(true);
                } else {
                    try {
                        result = Utility.GSON.fromJson(response.errorBody().string(), AuthResult.class);
                        result.setSuccess(false);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                result.setType(AuthResult.LOGIN);
                result.setResCode(response.code());
                setCurrentUser(result);
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                setIsLoading(false);
                setCurrentUser(new AuthResult(0, -1));
            }
        });
//        setCurrentUser(getMockUser());
//        setIsLoading(false);
    }


    /**
     * Register
     */
    public void register(RegisterModel registerModel) {

        setIsLoading(true);

        authServices.register(registerModel).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                setIsLoading(false);

                AuthResult result = new AuthResult();

                if (response.isSuccessful()) {
                    result = response.body();
                    result.setSuccess(true);
                } else {
                    try {
                        Gson gson = new Gson();
                        result = gson.fromJson(response.errorBody().string(), AuthResult.class);
                        result.setSuccess(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                result.setResCode(response.code());
                result.setType(AuthResult.REGISTER);
                currentUser.setValue(result);
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                setIsLoading(false);
                currentUser.setValue(new AuthResult(1, -1));
            }
        });
    }

    /**
     * Logout
     */
    public void logout() {
        if (currentUser != null) setCurrentUser(null);
    }


    public ArrayList<UserModel> getRestaurants() {
        ArrayList<UserModel> mockList = new ArrayList<>();
        UserModel mockRestaurant = getMockRestaurant();
        for (int i=0; i < 20; i++) {
            mockList.add(mockRestaurant);
        }
        return mockList;
    }

    public AuthResult getMockUser() {
        UserModel user = new UserModel();
        user.setAdmin(false);
        user.setLastName("boop");
        user.setFirstName("boop");
        user.setEmail("boop@gmail.com");
        user.setPhone("0600000000");
        user.setType("client");

        AddressModel address = new AddressModel();
        address.setStreet("1 rue des amandiers");
        address.setAdditional("");
        address.setCity("Montpellier");
        address.setZip("34000");
        address.setCountry("France");
        user.setAddress(address);

        AuthResult result = new AuthResult();
        result.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI2MDdlYjU5ZDYyZTBlMTAwNzg4MzVkYjgiLCJpYXQiOjE2MTk1MzU2MTMsImV4cCI6MTYyMDc0NTIxM30.9-Cfn75I0V8b7suoysCiNcnJ2STUGXAWCLHi24a8Io4");
        result.setExpires(1620745213979L);
        result.resCode = 200;
        result.success = true;
        result.setUser(user);
        result.setType(AuthResult.LOGIN);

        return result;
    }

    public UserModel getMockRestaurant() {
        UserModel user = new UserModel();
        user.setAdmin(false);
        user.setLastName("boop");
        user.setFirstName("boop");
        user.setEmail("boop@gmail.com");
        user.setPhone("0600000000");
        user.setType("restaurant");

        AddressModel address = new AddressModel();
        address.setStreet("1 rue des amandiers");
        address.setAdditional("");
        address.setCity("Montpellier");
        address.setZip("34000");
        address.setCountry("France");
        user.setAddress(address);

        user.restaurantName = "Grand Slam Burger";

        return user;
    }
}
package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.dtos.UpdateProfileRequest;
import org.yearup.errors.UserNotFound;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Profile getProfile(int userId){
        return this.profileRepository.findById(userId).orElseThrow(()->  new UserNotFound("User with id: "+ userId + " was not found"));
    }

    public  void updateProfile(int userId, UpdateProfileRequest updateProfileRequest){
         this.profileRepository.findById(userId).ifPresentOrElse(
               (p)->{
                    p.setAddress(updateProfileRequest.getAddress());
                    p.setCity(updateProfileRequest.getCity());
                    p.setEmail(updateProfileRequest.getEmail());
                    p.setPhone(updateProfileRequest.getPhone());
                    p.setState(updateProfileRequest.getState());
                    p.setFirstName(updateProfileRequest.getFirstName());
                    p.setLastName(updateProfileRequest.getLastName());
                    p.setZip(updateProfileRequest.getZip());
                    this.profileRepository.save(p);
               },
               ()-> new UserNotFound("User not found with id: "+ userId));

    }
}

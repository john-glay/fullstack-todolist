package johnglay.fullstacktodolist.controller;

import johnglay.fullstacktodolist.model.UserProfile;
import johnglay.fullstacktodolist.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/image")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/getUserProfiles")
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PutMapping(path = "/{userProfileId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadUserProfileImage(@PathVariable UUID userProfileId, @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(userProfileId, file);
    }

    @GetMapping("/{userProfileId}/download")
    public byte[] downloadUserProfileImage(@PathVariable UUID userProfileId) {
        return userProfileService.downloadUserProfileImage(userProfileId);
    }

}
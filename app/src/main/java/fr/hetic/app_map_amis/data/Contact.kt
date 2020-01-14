package fr.hetic.app_map_amis.data

data class Contact(var image: String, var username: String) {

    override fun toString(): String {
        return "Contact(image='$image', username='$username')"
    }

}
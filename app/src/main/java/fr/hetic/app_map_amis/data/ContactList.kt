package fr.hetic.app_map_amis.data

class ContactList {

    companion object{

        fun createDataSet(): ArrayList<Contact>{
            val list = ArrayList<Contact>()
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Sabrina"
                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Nina"
                )
            )

            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Justin"
                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "John"
                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Martin"
                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Téo"
                )
            )

            return list
        }
    }
}